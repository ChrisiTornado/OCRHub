import {Component, OnInit, signal} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {DatePipe, NgIf} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';

import {DocumentModel} from '../../../models/document.model';
import {DocumentService} from '../../../services/document.service';
import {MdbButtonComponent} from '../../utils/mdb-button/mdb-button.component';

@Component({
  selector: 'app-document',
  standalone: true,
  imports: [
    DatePipe,
    MdbButtonComponent,
    NgIf,
    RouterLink,
    TranslateModule
  ],
  templateUrl: './document.component.html',
  styleUrl: './document.component.scss'
})
export class DocumentComponent implements OnInit {
  document = signal<DocumentModel | null>(null);
  loading = signal<boolean>(true);
  error = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private documentService: DocumentService
  ) {
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error.set('NAVIGATION.DOCUMENTS_RESOURCE_NOT_FOUND');
      this.loading.set(false);
      return;
    }

    this.documentService.getOne(id).subscribe({
      next: (document) => {
        this.document.set(document);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('NAVIGATION.DOCUMENTS_RESOURCE_NOT_FOUND');
        this.loading.set(false);
      }
    });
  }
}
