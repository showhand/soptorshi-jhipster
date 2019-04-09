import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';
import { AccountService } from 'app/core';
import { ExperienceInformationAttachmentService } from './experience-information-attachment.service';

@Component({
    selector: 'jhi-experience-information-attachment',
    templateUrl: './experience-information-attachment.component.html'
})
export class ExperienceInformationAttachmentComponent implements OnInit, OnDestroy {
    experienceInformationAttachments: IExperienceInformationAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected experienceInformationAttachmentService: ExperienceInformationAttachmentService,
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
            this.experienceInformationAttachmentService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IExperienceInformationAttachment[]>) => res.ok),
                    map((res: HttpResponse<IExperienceInformationAttachment[]>) => res.body)
                )
                .subscribe(
                    (res: IExperienceInformationAttachment[]) => (this.experienceInformationAttachments = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.experienceInformationAttachmentService
            .query()
            .pipe(
                filter((res: HttpResponse<IExperienceInformationAttachment[]>) => res.ok),
                map((res: HttpResponse<IExperienceInformationAttachment[]>) => res.body)
            )
            .subscribe(
                (res: IExperienceInformationAttachment[]) => {
                    this.experienceInformationAttachments = res;
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
        this.registerChangeInExperienceInformationAttachments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExperienceInformationAttachment) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInExperienceInformationAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('experienceInformationAttachmentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
