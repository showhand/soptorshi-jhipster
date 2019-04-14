import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

@Component({
    selector: 'jhi-experience-information-attachment-detail',
    templateUrl: './experience-information-attachment-detail.component.html'
})
export class ExperienceInformationAttachmentDetailComponent implements OnInit {
    @Input()
    experienceInformationAttachment: IExperienceInformationAttachment;
    @Output()
    showExperienceInformationAttachmentSection: EventEmitter<any> = new EventEmitter();
    @Output()
    editExperienceInformationAttachment: EventEmitter<any> = new EventEmitter();

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {}

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    edit() {
        this.editExperienceInformationAttachment.emit();
    }
    previousState() {
        this.showExperienceInformationAttachmentSection.emit();
    }
}
