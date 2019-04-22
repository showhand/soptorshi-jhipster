import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

@Component({
    selector: 'jhi-academic-information-attachment-detail',
    templateUrl: './academic-information-attachment-detail.component.html'
})
export class AcademicInformationAttachmentDetailComponent implements OnInit {
    academicInformationAttachment: IAcademicInformationAttachment;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ academicInformationAttachment }) => {
            this.academicInformationAttachment = academicInformationAttachment;
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
