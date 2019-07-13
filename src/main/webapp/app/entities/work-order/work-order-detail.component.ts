import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IWorkOrder } from 'app/shared/model/work-order.model';

@Component({
    selector: 'jhi-work-order-detail',
    templateUrl: './work-order-detail.component.html'
})
export class WorkOrderDetailComponent implements OnInit {
    workOrder: IWorkOrder;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ workOrder }) => {
            this.workOrder = workOrder;
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
