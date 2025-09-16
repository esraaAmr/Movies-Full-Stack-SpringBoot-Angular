import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent {
  @Input() size: 'small' | 'medium' | 'large' = 'medium';
  @Input() message: string = 'Loading...';
  @Input() overlay: boolean = false;
  @Input() color: 'primary' | 'secondary' | 'accent' = 'primary';
}
