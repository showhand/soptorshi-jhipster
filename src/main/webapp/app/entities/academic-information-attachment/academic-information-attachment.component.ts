import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { AcademicInformationAttachment, IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';
import { AccountService } from 'app/core';
import { AcademicInformationAttachmentService } from './academic-information-attachment.service';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-academic-information-attachment',
    templateUrl: './academic-information-attachment.component.html'
})
export class AcademicInformationAttachmentComponent implements OnInit, OnDestroy {
    @Input()
    employee: IEmployee;
    academicInformationAttachment: IAcademicInformationAttachment;
    academicInformationAttachments: IAcademicInformationAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    showAcademicInformationAttachmentSection: boolean;
    showAddOrUpdateSection: boolean;
    showDeleteDialog: boolean;

    constructor(
        protected academicInformationAttachmentService: AcademicInformationAttachmentService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.academicInformationAttachmentService.findByEmployee(this.employee.id).subscribe(
            (res: HttpResponse<IAcademicInformationAttachment[]>) => {
                this.academicInformationAttachments = res.body;
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
        this.showAcademicInformationAttachmentSection = true;
        this.showDeleteDialog = false;
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

    showAcademicInformationAttachment() {
        this.loadAll();
        this.showAcademicInformationAttachmentSection = true;
        this.showDeleteDialog = false;
        this.academicInformationAttachment = new AcademicInformationAttachment();
        this.academicInformationAttachment.employeeId = this.employee.id;
    }

    delete(academicInformationAttachment: IAcademicInformationAttachment) {
        this.academicInformationAttachment = academicInformationAttachment;
        this.showDeleteDialog = true;
    }

    addAcademicInformationAttachment() {
        this.academicInformationAttachment = new AcademicInformationAttachment();
        this.academicInformationAttachment.employeeId = this.employee.id;
        this.showAddOrUpdateSection = true;
        this.showAcademicInformationAttachmentSection = false;
    }

    edit(academicInformationAttachment: IAcademicInformationAttachment) {
        this.academicInformationAttachment = academicInformationAttachment;
        this.showAcademicInformationAttachmentSection = false;
        this.showAddOrUpdateSection = true;
    }

    editAcademicInformationAttachment() {
        this.showAcademicInformationAttachmentSection = false;
        this.showAddOrUpdateSection = true;
    }

    viewAcademicInformationAttachment(academicInformationAttachment: IAcademicInformationAttachment) {
        this.academicInformationAttachment = academicInformationAttachment;
        this.showAcademicInformationAttachmentSection = false;
        this.showAddOrUpdateSection = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
