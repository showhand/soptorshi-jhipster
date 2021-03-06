import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';
import { AccountService } from 'app/core';
import { TrainingInformationAttachmentService } from './training-information-attachment.service';

@Component({
    selector: 'jhi-training-information-attachment',
    templateUrl: './training-information-attachment.component.html'
})
export class TrainingInformationAttachmentComponent implements OnInit, OnDestroy {
    trainingInformationAttachments: ITrainingInformationAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected trainingInformationAttachmentService: TrainingInformationAttachmentService,
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
            this.trainingInformationAttachmentService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ITrainingInformationAttachment[]>) => res.ok),
                    map((res: HttpResponse<ITrainingInformationAttachment[]>) => res.body)
                )
                .subscribe(
                    (res: ITrainingInformationAttachment[]) => (this.trainingInformationAttachments = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.trainingInformationAttachmentService
            .query()
            .pipe(
                filter((res: HttpResponse<ITrainingInformationAttachment[]>) => res.ok),
                map((res: HttpResponse<ITrainingInformationAttachment[]>) => res.body)
            )
            .subscribe(
                (res: ITrainingInformationAttachment[]) => {
                    this.trainingInformationAttachments = res;
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
        this.registerChangeInTrainingInformationAttachments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITrainingInformationAttachment) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInTrainingInformationAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('trainingInformationAttachmentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
