import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationExtendedService } from './requisition-voucher-relation-extended.service';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { RequisitionVoucherRelationUpdateComponent } from 'app/entities/requisition-voucher-relation';
import { VoucherService } from 'app/entities/voucher';

@Component({
    selector: 'jhi-requisition-voucher-relation-update',
    templateUrl: './requisition-voucher-relation-extended-update.component.html'
})
export class RequisitionVoucherRelationExtendedUpdateComponent extends RequisitionVoucherRelationUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionVoucherRelationService: RequisitionVoucherRelationExtendedService,
        protected voucherService: VoucherService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, requisitionVoucherRelationService, voucherService, requisitionService, activatedRoute);
    }
}
