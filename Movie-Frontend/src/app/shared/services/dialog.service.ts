import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { filter } from 'rxjs/operators';

export interface DialogConfig {
  title: string;
  message: string;
  type: 'info' | 'success' | 'warning' | 'error' | 'confirm';
  confirmText?: string;
  cancelText?: string;
  icon?: string;
  showCancel?: boolean;
}

export interface DialogResult {
  confirmed: boolean;
  data?: any;
}

@Injectable({
  providedIn: 'root'
})
export class DialogService {
  private dialogSubject = new BehaviorSubject<DialogConfig | null>(null);
  private resultSubject = new BehaviorSubject<DialogResult | null>(null);

  showDialog(config: DialogConfig): Observable<DialogResult> {
    this.resultSubject.next(null);
    this.dialogSubject.next(config);
    return this.resultSubject.asObservable().pipe(
      filter(result => result !== null)
    ) as Observable<DialogResult>;
  }

  getDialogConfig(): Observable<DialogConfig | null> {
    return this.dialogSubject.asObservable();
  }

  confirm(result: DialogResult): void {
    this.resultSubject.next(result);
    this.closeDialog();
  }

  closeDialog(): void {
    this.dialogSubject.next(null);
  }

  showAlert(title: string, message: string, type: 'info' | 'success' | 'warning' | 'error' = 'info'): Observable<DialogResult> {
    return this.showDialog({
      title,
      message,
      type,
      confirmText: 'OK',
      showCancel: false
    });
  }

  showConfirm(title: string, message: string, confirmText: string = 'Confirm', cancelText: string = 'Cancel'): Observable<DialogResult> {
    return this.showDialog({
      title,
      message,
      type: 'confirm',
      confirmText,
      cancelText,
      showCancel: true
    });
  }
}
