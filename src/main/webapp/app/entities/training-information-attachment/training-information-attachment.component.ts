import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITrainingInformationAttachment, TrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';
import { AccountService } from 'app/core';
import { TrainingInformationAttachmentService } from './training-information-attachment.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { AcademicInformationAttachment, IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

@Component({
    selector: 'jhi-training-information-attachment',
    templateUrl: './training-information-attachment.component.html'
})
export class TrainingInformationAttachmentComponent implements OnInit, OnDestroy {
    @Input()
    employee: IEmployee;
    @Output()
    closeEmployeeManagement: EventEmitter<any> = new EventEmitter();
    trainingInformationAttachment: ITrainingInformationAttachment;
    trainingInformationAttachments: ITrainingInformationAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    showTrainingInformationAttachmentSection: boolean;
    showAddOrUpdateSection: boolean;
    showDeleteDialog: boolean;

    constructor(
        protected trainingInformationAttachmentService: TrainingInformationAttachmentService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.trainingInformationAttachmentService.findByEmployee(this.employee.id).subscribe(
            (res: HttpResponse<ITrainingInformationAttachment[]>) => {
                this.trainingInformationAttachments = res.body;
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
        this.showTrainingInformationAttachmentSection = true;
        this.showDeleteDialog = false;
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

    showTrainingInformationAttachment() {
        this.loadAll();
        this.showTrainingInformationAttachmentSection = true;
        this.showDeleteDialog = false;
        this.trainingInformationAttachment = new TrainingInformationAttachment();
        this.trainingInformationAttachment.employeeId = this.employee.id;
    }

    delete(trainingInformationAttachment: IAcademicInformationAttachment) {
        this.trainingInformationAttachment = trainingInformationAttachment;
        this.showDeleteDialog = true;
    }

    addTrainingInformationAttachment() {
        this.trainingInformationAttachment = new TrainingInformationAttachment();
        this.trainingInformationAttachment.employeeId = this.employee.id;
        this.showAddOrUpdateSection = true;
        this.showTrainingInformationAttachmentSection = false;
    }

    edit(trainingInformationAttachment: IAcademicInformationAttachment) {
        this.trainingInformationAttachment = trainingInformationAttachment;
        this.showTrainingInformationAttachmentSection = false;
        this.showAddOrUpdateSection = true;
    }

    editTrainingInformationAttachment() {
        this.showTrainingInformationAttachmentSection = false;
        this.showAddOrUpdateSection = true;
    }

    viewTrainingInformationAttachment(trainingInformationAttachment: IAcademicInformationAttachment) {
        this.trainingInformationAttachment = trainingInformationAttachment;
        this.showTrainingInformationAttachmentSection = false;
        this.showAddOrUpdateSection = false;
    }
}
