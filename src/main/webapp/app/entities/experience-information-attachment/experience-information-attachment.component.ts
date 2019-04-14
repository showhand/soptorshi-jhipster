import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import {
    ExperienceInformationAttachment,
    IExperienceInformationAttachment
} from 'app/shared/model/experience-information-attachment.model';
import { AccountService } from 'app/core';
import { ExperienceInformationAttachmentService } from './experience-information-attachment.service';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-experience-information-attachment',
    templateUrl: './experience-information-attachment.component.html'
})
export class ExperienceInformationAttachmentComponent implements OnInit, OnDestroy {
    @Input()
    employee: IEmployee;
    experienceInformationAttachment: IExperienceInformationAttachment;
    experienceInformationAttachments: IExperienceInformationAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    showExperienceInformationAttachmentSection: boolean;
    showAddOrUpdateSection: boolean;
    showDeleteDialog: boolean;

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
        this.experienceInformationAttachmentService.findByEmployee(this.employee.id).subscribe(
            (res: HttpResponse<IExperienceInformationAttachment[]>) => {
                this.experienceInformationAttachments = res.body;
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
        this.showExperienceInformationAttachmentSection = true;
        this.showDeleteDialog = false;
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

    showExperienceInformationAttachment() {
        this.loadAll();
        this.showExperienceInformationAttachmentSection = true;
        this.showDeleteDialog = false;
        this.experienceInformationAttachment = new ExperienceInformationAttachment();
        this.experienceInformationAttachment.employeeId = this.employee.id;
    }

    delete(experienceInformationAttachment: IExperienceInformationAttachment) {
        this.experienceInformationAttachment = experienceInformationAttachment;
        this.showDeleteDialog = true;
    }

    addexperienceInformationAttachment() {
        this.experienceInformationAttachment = new ExperienceInformationAttachment();
        this.experienceInformationAttachment.employeeId = this.employee.id;
        this.showAddOrUpdateSection = true;
        this.showExperienceInformationAttachmentSection = false;
    }

    edit(experienceInformationAttachment: IExperienceInformationAttachment) {
        this.experienceInformationAttachment = experienceInformationAttachment;
        this.showExperienceInformationAttachmentSection = false;
        this.showAddOrUpdateSection = true;
    }

    editexperienceInformationAttachment() {
        this.showExperienceInformationAttachmentSection = false;
        this.showAddOrUpdateSection = true;
    }

    viewexperienceInformationAttachment(experienceInformationAttachment: IExperienceInformationAttachment) {
        this.experienceInformationAttachment = experienceInformationAttachment;
        this.showExperienceInformationAttachmentSection = false;
        this.showAddOrUpdateSection = false;
    }
}
