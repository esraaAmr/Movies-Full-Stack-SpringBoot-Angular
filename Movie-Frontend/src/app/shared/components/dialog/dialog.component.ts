import { Component, OnInit, OnDestroy } from '@angular/core';
import { DialogConfig, DialogResult, DialogService } from '../../services/dialog.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements OnInit, OnDestroy {
  dialogConfig: DialogConfig | null = null;
  private subscription: Subscription = new Subscription();

  constructor(private dialogService: DialogService) { }

  ngOnInit(): void {
    this.subscription.add(
      this.dialogService.getDialogConfig().subscribe(config => {
        this.dialogConfig = config;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onConfirm(): void {
    this.dialogService.confirm({ confirmed: true });
  }

  onCancel(): void {
    this.dialogService.confirm({ confirmed: false });
  }

  onBackdropClick(event: Event): void {
    if (event.target === event.currentTarget) {
      this.onCancel();
    }
  }

  getIconClass(): string {
    if (!this.dialogConfig) return '';
    
    switch (this.dialogConfig.type) {
      case 'success':
        return 'fas fa-check-circle';
      case 'error':
        return 'fas fa-exclamation-circle';
      case 'warning':
        return 'fas fa-exclamation-triangle';
      case 'confirm':
        return 'fas fa-question-circle';
      default:
        return 'fas fa-info-circle';
    }
  }

  getTypeClass(): string {
    if (!this.dialogConfig) return '';
    return `dialog-${this.dialogConfig.type}`;
  }
}
