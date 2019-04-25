import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';

@Component({
    selector: 'jhi-training-information-attachment-detail',
    templateUrl: './training-information-attachment-detail.component.html'
})
export class TrainingInformationAttachmentDetailComponent implements OnInit {
    trainingInformationAttachment: ITrainingInformationAttachment;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainingInformationAttachment }) => {
            this.trainingInformationAttachment = trainingInformationAttachment;
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
