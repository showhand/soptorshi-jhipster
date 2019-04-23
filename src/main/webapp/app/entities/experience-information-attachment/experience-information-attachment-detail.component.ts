import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

@Component({
    selector: 'jhi-experience-information-attachment-detail',
    templateUrl: './experience-information-attachment-detail.component.html'
})
export class ExperienceInformationAttachmentDetailComponent implements OnInit {
    experienceInformationAttachment: IExperienceInformationAttachment;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experienceInformationAttachment }) => {
            this.experienceInformationAttachment = experienceInformationAttachment;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
