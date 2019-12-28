import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';

@Component({
    selector: 'jhi-requisition-messages-detail',
    templateUrl: './requisition-messages-detail.component.html'
})
export class RequisitionMessagesDetailComponent implements OnInit {
    requisitionMessages: IRequisitionMessages;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisitionMessages }) => {
            this.requisitionMessages = requisitionMessages;
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
