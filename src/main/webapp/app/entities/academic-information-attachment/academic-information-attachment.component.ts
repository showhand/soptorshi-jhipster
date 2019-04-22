import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';
import { AccountService } from 'app/core';
import { AcademicInformationAttachmentService } from './academic-information-attachment.service';

@Component({
    selector: 'jhi-academic-information-attachment',
    templateUrl: './academic-information-attachment.component.html'
})
export class AcademicInformationAttachmentComponent implements OnInit, OnDestroy {
    academicInformationAttachments: IAcademicInformationAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected academicInformationAttachmentService: AcademicInformationAttachmentService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.academicInformationAttachmentService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IAcademicInformationAttachment[]>) => res.ok),
                    map((res: HttpResponse<IAcademicInformationAttachment[]>) => res.body)
                )
                .subscribe(
                    (res: IAcademicInformationAttachment[]) => (this.academicInformationAttachments = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.academicInformationAttachmentService
            .query()
            .pipe(
                filter((res: HttpResponse<IAcademicInformationAttachment[]>) => res.ok),
                map((res: HttpResponse<IAcademicInformationAttachment[]>) => res.body)
            )
            .subscribe(
                (res: IAcademicInformationAttachment[]) => {
                    this.academicInformationAttachments = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAcademicInformationAttachments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAcademicInformationAttachment) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInAcademicInformationAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('academicInformationAttachmentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
