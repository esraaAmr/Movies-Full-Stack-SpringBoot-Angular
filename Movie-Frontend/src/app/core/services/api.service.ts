import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

// DTOs matching your backend
export interface MovieDto {
  id?: number;
  title: string;
  year: number;
  imdbId: string;
  poster?: string;
}

export interface OmdbSearchResult {
  imdbID: string;
  Title: string;
  Year: string;
  Type: string;
  Poster: string;
}

export interface RatingDto {
  id?: number;
  movieId: number;
  userId: number;
  rating: number;
  comment?: string;
}

export interface UserDto {
  id?: number;
  username: string;
  password?: string;
  role?: 'ADMIN' | 'USER';
}

export interface BatchImportResult {
  imported: MovieDto[];
  skipped: string[];
  errors: string[];
}

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  // User APIs
  login(username: string, password: string): Observable<UserDto> {
    const params = new URLSearchParams();
    params.set('username', username);
    params.set('password', password);
    
    return this.http.post<UserDto>(`${this.baseUrl}/api/users/login?${params.toString()}`, {})
      .pipe(catchError(this.handleError));
  }

  // Movie APIs
  addMovie(movie: MovieDto): Observable<MovieDto> {
    return this.http.post<MovieDto>(`${this.baseUrl}/api/movies`, movie)
      .pipe(catchError(this.handleError));
  }

  getAllMovies(): Observable<MovieDto[]> {
    return this.http.get<MovieDto[]>(`${this.baseUrl}/api/movies`)
      .pipe(catchError(this.handleError));
  }

  getMovieById(id: number): Observable<MovieDto> {
    return this.http.get<MovieDto>(`${this.baseUrl}/api/movies/${id}`)
      .pipe(catchError(this.handleError));
  }

  searchMovies(title: string): Observable<MovieDto[]> {
    const params = new URLSearchParams();
    params.set('title', title);
    
    return this.http.get<MovieDto[]>(`${this.baseUrl}/api/movies/search?${params.toString()}`)
      .pipe(catchError(this.handleError));
  }

  // OMDB APIs
  searchMoviesFromOmdb(keyword: string): Observable<OmdbSearchResult[]> {
    const params = new URLSearchParams();
    params.set('keyword', keyword);
    
    return this.http.get<OmdbSearchResult[]>(`${this.baseUrl}/api/movies/omdb/search/list?${params.toString()}`)
      .pipe(catchError(this.handleError));
  }

  importMovieFromOmdb(imdbId: string): Observable<MovieDto> {
    const params = new URLSearchParams();
    params.set('imdbId', imdbId);
    
    return this.http.post<MovieDto>(`${this.baseUrl}/api/movies/omdb/import?${params.toString()}`, {})
      .pipe(catchError(this.handleError));
  }

  importMoviesFromOmdb(imdbIds: string[]): Observable<BatchImportResult> {
    return this.http.post<BatchImportResult>(`${this.baseUrl}/api/movies/omdb/import/list`, imdbIds)
      .pipe(catchError(this.handleError));
  }

  removeMovie(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/api/movies/${id}`, {
      observe: 'response'
    }).pipe(
      catchError(this.handleError)
    );
  }

  removeMovies(ids: number[]): Observable<any> {
    return this.http.delete(`${this.baseUrl}/api/movies/batch`, {
      body: ids,
      observe: 'response'
    }).pipe(
      catchError(this.handleError)
    );
  }

  // Rating APIs
  addRating(rating: RatingDto): Observable<RatingDto> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });
    
    
    return this.http.post<RatingDto>(`${this.baseUrl}/api/ratings`, rating, { 
      headers,
      observe: 'response' // This will give us the full response
    }).pipe(
      tap(response => {
        // Response received
      }),
      map(response => response.body!), // Extract the body
      catchError(this.handleError)
    );
  }

  getRatingsByMovie(movieId: number): Observable<RatingDto[]> {
    return this.http.get<RatingDto[]>(`${this.baseUrl}/api/ratings/movie/${movieId}`)
      .pipe(catchError(this.handleError));
  }

  getRatingsByUser(userId: number): Observable<RatingDto[]> {
    return this.http.get<RatingDto[]>(`${this.baseUrl}/api/ratings/user/${userId}`)
      .pipe(catchError(this.handleError));
  }

  // Error handling
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      switch (error.status) {
        case 400:
          errorMessage = 'Bad request. Please check your input.';
          break;
        case 404:
          errorMessage = 'Resource not found.';
          break;
        case 409:
          errorMessage = 'Resource already exists.';
          break;
        case 502:
          errorMessage = 'External service error. Please try again later.';
          break;
        case 500:
          errorMessage = 'Internal server error. Please try again later.';
          break;
        default:
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
    }
    
    return throwError(() => new Error(errorMessage));
  }
}