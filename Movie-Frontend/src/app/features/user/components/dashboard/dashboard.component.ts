import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, User } from '../../../../core/services/auth.service';
import { ApiService, MovieDto, RatingDto } from '../../../../core/services/api.service';
import { DialogService } from '../../../../shared/services/dialog.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class UserDashboardComponent implements OnInit {
  currentUser: User | null = null;
  movies: MovieDto[] = [];
  filteredMovies: MovieDto[] = [];
  searchQuery: string = '';
  isLoadingMovies: boolean = false;
  currentPage: number = 1;
  itemsPerPage: number = 12;
  totalPages: number = 0;
  selectedMovie: MovieDto | null = null;
  showMovieDetails: boolean = false;
  
  // Rating functionality
  userRatings: Map<number, RatingDto> = new Map();
  newRating: number = 5;
  newComment: string = '';
  isSubmittingRating: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private apiService: ApiService,
    private dialogService: DialogService,
  ) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.loadMovies();
    if (this.currentUser?.id) {
      this.loadUserRatings();
    }
  }

  loadMovies(): void {
    this.isLoadingMovies = true;
    this.apiService.getAllMovies().subscribe({
      next: (movies: MovieDto[]) => {
        this.movies = movies;
        this.filteredMovies = movies;
        this.calculatePagination();
        this.isLoadingMovies = false;
      },
      error: (error: any) => {
        this.isLoadingMovies = false;
      }
    });
  }

  searchMovies(): void {
    if (!this.searchQuery.trim()) {
      this.filteredMovies = this.movies;
    } else {
      this.filteredMovies = this.movies.filter(movie =>
        movie.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        movie.year.toString().includes(this.searchQuery) ||
        movie.imdbId.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
    this.currentPage = 1;
    this.calculatePagination();
  }

  calculatePagination(): void {
    this.totalPages = Math.ceil(this.filteredMovies.length / this.itemsPerPage);
  }

  get paginatedMovies(): MovieDto[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.filteredMovies.slice(startIndex, endIndex);
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    const maxVisiblePages = 5;
    const startPage = Math.max(1, this.currentPage - Math.floor(maxVisiblePages / 2));
    const endPage = Math.min(this.totalPages, startPage + maxVisiblePages - 1);

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }
    return pages;
  }

  viewMovieDetails(movie: MovieDto): void {
    this.selectedMovie = movie;
    this.showMovieDetails = true;
  }

  closeMovieDetails(): void {
    this.showMovieDetails = false;
    this.selectedMovie = null;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // Rating functionality
  loadUserRatings(): void {
    if (!this.currentUser?.id) return;
    
    this.apiService.getRatingsByUser(this.currentUser.id).subscribe({
      next: (ratings) => {
        this.userRatings.clear();
        ratings.forEach(rating => {
          this.userRatings.set(rating.movieId, rating);
        });
      },
      error: (error) => {
      }
    });
  }

  getUserRating(movieId: number | undefined): RatingDto | undefined {
    return movieId ? this.userRatings.get(movieId) : undefined;
  }

  hasUserRated(movieId: number | undefined): boolean {
    return movieId ? this.userRatings.has(movieId) : false;
  }

  submitRating(movieId: number): void {

    if (!this.currentUser?.id) {
      this.dialogService.showAlert('Authentication Required', 'Please log in to rate movies.', 'warning');
      return;
    }

    if (!movieId || isNaN(movieId)) {
      this.dialogService.showAlert('Invalid Input', 'Error: Invalid movie ID. Please try again.', 'error');
      return;
    }

    if (this.newRating < 1 || this.newRating > 5) {
      this.dialogService.showAlert('Invalid Rating', 'Please select a rating between 1 and 5.', 'warning');
      return;
    }

    this.isSubmittingRating = true;

    // Create rating object without undefined values
    const ratingDto: any = {
      movieId: Number(movieId),  // Ensure movieId is a number
      userId: Number(this.currentUser.id),  // Ensure userId is a number
      rating: Number(this.newRating)  // Ensure rating is a number
    };

    // Only add comment if it's not empty
    const trimmedComment = this.newComment.trim();
    if (trimmedComment) {
      ratingDto.comment = trimmedComment;
    }


    this.apiService.addRating(ratingDto).subscribe({
      next: (savedRating) => {
        this.userRatings.set(movieId, savedRating);
        this.newRating = 5;
        this.newComment = '';
        this.isSubmittingRating = false;
      },
      error: (error) => {
        this.isSubmittingRating = false;
        console.error('Error submitting rating:', error);
      }
    });
  }

  updateRating(movieId: number): void {
    if (!this.currentUser?.id) return;

    this.isSubmittingRating = true;

    const existingRating = this.userRatings.get(movieId);
    if (!existingRating) return;

    // Create rating object without undefined values
    const updatedRating: any = {
      movieId: Number(movieId),  // Ensure movieId is a number
      userId: Number(this.currentUser.id),  // Ensure userId is a number
      rating: Number(this.newRating)  // Ensure rating is a number
    };

    // Only add comment if it's not empty
    const trimmedComment = this.newComment.trim();
    if (trimmedComment) {
      updatedRating.comment = trimmedComment;
    }


    this.apiService.addRating(updatedRating).subscribe({
      next: (savedRating) => {
        this.userRatings.set(movieId, savedRating);
        this.newRating = 5;
        this.newComment = '';
        this.isSubmittingRating = false;
      },
      error: (error) => {
        this.isSubmittingRating = false;
        console.error('Error updating rating:', error);
      }
    });
  }


  cancelRating(): void {
    this.newRating = 5;
    this.newComment = '';
  }

  onRatingChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    this.newRating = Number(target.value);
  }

}
