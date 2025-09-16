import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, User } from '../../../../core/services/auth.service';
import { ApiService, MovieDto, OmdbSearchResult } from '../../../../core/services/api.service';
import { DialogService } from '../../../../shared/services/dialog.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  currentUser: User | null = null;
  
  // Search functionality
  searchQuery = '';
  searchResults: OmdbSearchResult[] = [];
  isSearching = false;
  
  // Batch operations
  selectedMovies: Set<number> = new Set();
  isBatchMode = false;
  
  // Prevent duplicate operations
  isDeletingMovie = false;
  isBatchDeleting = false;
  
  // Database movies
  dbMovies: MovieDto[] = [];
  isLoadingMovies = false;
  
  // Pagination
  currentPage = 1;
  itemsPerPage = 6;
  totalPages = 0;

  constructor(
    private authService: AuthService,
    private router: Router,
    private apiService: ApiService,
    private dialogService: DialogService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.loadMoviesFromDB();
    this.testApiConnection();
  }

  testApiConnection(): void {
    // Test with a simple GET request
    this.apiService.getAllMovies().subscribe({
      next: (movies) => {
        // API connection successful
      },
      error: (error) => {
        // API connection failed
      }
    });
  }


  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  searchMovies(): void {
    if (!this.searchQuery.trim()) {
      this.searchResults = [];
      return;
    }

    this.isSearching = true;
    this.apiService.searchMoviesFromOmdb(this.searchQuery).subscribe({
      next: (movies) => {
        this.searchResults = movies;
        this.isSearching = false;
      },
      error: (error) => {
        this.searchResults = [];
        this.isSearching = false;
        console.error('Error searching movies:', error);
      }
    });
  }

  addMovieToDB(movie: OmdbSearchResult): void {
    this.apiService.importMovieFromOmdb(movie.imdbID).subscribe({
      next: (addedMovie) => {
        this.loadMoviesFromDB();
        // Remove from search results
        this.searchResults = this.searchResults.filter(m => m.imdbID !== movie.imdbID);
      },
      error: (error) => {
        if (error.message.includes('already exists')) {
          console.warn('Movie already exists in database');
        } else {
          console.error('Error adding movie to database:', error);
        }
      }
    });
  }

  removeMovieFromDB(movieId: number): void {
    // Prevent duplicate calls
    if (this.isDeletingMovie) {
      return;
    }
    
    if (!movieId || movieId === 0) {
      this.dialogService.showAlert('Invalid Input', 'Error: Movie ID is missing or invalid. Cannot remove movie.', 'error');
      return;
    }

    this.dialogService.showConfirm(
      'Confirm Deletion', 
      'Are you sure you want to remove this movie from the database?',
      'Delete',
      'Cancel'
    ).subscribe(result => {
      // Only proceed if user confirmed
      if (result.confirmed) {
        this.isDeletingMovie = true;
        
        // Make API call ONLY after user confirms
        this.apiService.removeMovie(movieId).subscribe({
          next: (response) => {
            if (response.status >= 200 && response.status < 300) {
              // No notification - just refresh the list silently
              this.loadMoviesFromDB(false);
            } else {
              console.warn('Movie removal completed with unexpected status:', response.status);
              this.loadMoviesFromDB();
            }
            this.isDeletingMovie = false;
          },
          error: (error) => {
            let errorMessage = 'Unknown error occurred';
            if (error.status === 0) {
              errorMessage = 'Network error - is your backend running?';
            } else if (error.status === 404) {
              errorMessage = 'Movie not found (404)';
            } else if (error.status === 500) {
              errorMessage = 'Server error occurred (500)';
            } else if (error.status === 403) {
              errorMessage = 'Access forbidden (403)';
            } else if (error.status === 401) {
              errorMessage = 'Unauthorized (401)';
            } else if (error.message) {
              errorMessage = error.message;
            }
            
            console.error('Error removing movie from database:', errorMessage);
            this.isDeletingMovie = false;
          }
        });
      }
      
    });
  }

  loadMoviesFromDB(showErrorNotification: boolean = true): void {
    this.isLoadingMovies = true;
    this.apiService.getAllMovies().subscribe({
      next: (movies) => {
        this.dbMovies = movies;
        this.totalPages = Math.ceil(movies.length / this.itemsPerPage);
        this.isLoadingMovies = false;
      },
      error: (error) => {
        this.isLoadingMovies = false;
        if (showErrorNotification) {
          console.error('Error loading movies from database:', error);
        }
      }
    });
  }

  get paginatedMovies(): MovieDto[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.dbMovies.slice(startIndex, endIndex);
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    for (let i = 1; i <= this.totalPages; i++) {
      pages.push(i);
    }
    return pages;
  }

  toggleBatchMode(): void {
    this.isBatchMode = !this.isBatchMode;
    this.selectedMovies.clear();
  }

  toggleMovieSelection(movieId: number): void {
    if (this.selectedMovies.has(movieId)) {
      this.selectedMovies.delete(movieId);
    } else {
      this.selectedMovies.add(movieId);
    }
  }

  isMovieSelected(movieId: number): boolean {
    return this.selectedMovies.has(movieId);
  }

  batchDeleteMovies(): void {
    // Prevent duplicate calls
    if (this.isBatchDeleting) {
      return;
    }

    if (this.selectedMovies.size === 0) {
      this.dialogService.showAlert('No Selection', 'Please select movies to delete.', 'warning');
      return;
    }

    // Show confirmation dialog first
    this.dialogService.showConfirm(
      'Confirm Batch Deletion', 
      `Are you sure you want to delete ${this.selectedMovies.size} selected movies?`,
      'Delete All',
      'Cancel'
    ).subscribe(result => {
      // Only proceed if user confirmed
      if (result.confirmed) {
        this.isBatchDeleting = true;
        const movieIds = Array.from(this.selectedMovies);
        
        // Make API call to delete movies
        this.apiService.removeMovies(movieIds).subscribe({
          next: (response) => {
            // No notification - just refresh the list silently
            
            // Refresh the movie list without showing error notifications
            this.loadMoviesFromDB(false);
            
            // Clear selection and exit batch mode
            this.selectedMovies.clear();
            this.isBatchMode = false;
            this.isBatchDeleting = false;
          },
          error: (error) => {
            // Error occurred - could add console log or other handling here
            console.error('Error deleting movies:', error);
            this.isBatchDeleting = false;
          }
        });
      }
    });
  }

  batchAddMovies(): void {
    if (this.searchResults.length === 0) {
      this.dialogService.showAlert('No Movies', 'No movies to add. Please search for movies first.', 'warning');
      return;
    }

    const imdbIds = this.searchResults.map(movie => movie.imdbID);
    this.apiService.importMoviesFromOmdb(imdbIds).subscribe({
      next: (result) => {
        let message = `Import completed!\n`;
        message += `Imported: ${result.imported.length}\n`;
        message += `Skipped: ${result.skipped.length}\n`;
        message += `Errors: ${result.errors.length}`;
        
        this.loadMoviesFromDB(false); 
        this.searchResults = [];
        this.searchQuery = '';
      },
      error: (error) => {
        console.error('Error importing movies:', error);
      }
    });
  }
}
