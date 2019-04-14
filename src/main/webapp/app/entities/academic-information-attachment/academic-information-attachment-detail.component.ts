import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

@Component({
    selector: 'jhi-academic-information-attachment-detail',
    templateUrl: './academic-information-attachment-detail.component.html'
})
export class AcademicInformationAttachmentDetailComponent implements OnInit {
    @Input()
    academicInformationAttachment: IAcademicInformationAttachment;
    @Output()
    showAcademicInformationAttachmentSection: EventEmitter<any> = new EventEmitter<any>();
    @Output()
    editAcademicInformationAttachment: EventEmitter<any> = new EventEmitter();

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {}

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    edit() {
        this.editAcademicInformationAttachment.emit();
    }
    previousState() {
        this.showAcademicInformationAttachmentSection.emit();
    }
}
