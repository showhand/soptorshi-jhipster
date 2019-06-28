import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRequisition } from 'app/shared/model/requisition.model';

@Component({
    selector: 'jhi-requisition-detail',
    templateUrl: './requisition-detail.component.html'
})
export class RequisitionDetailComponent implements OnInit {
    requisition: IRequisition;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisition }) => {
            this.requisition = requisition;
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
