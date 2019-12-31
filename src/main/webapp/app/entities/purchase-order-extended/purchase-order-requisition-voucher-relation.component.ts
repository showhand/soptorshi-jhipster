import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import {
    RequisitionVoucherRelationExtendedComponent,
    RequisitionVoucherRelationExtendedService
} from 'app/entities/requisition-voucher-relation-extended';
import { RequisitionVoucherRelationService } from 'app/entities/requisition-voucher-relation';

@Component({
    selector: 'jhi-purchase-order-requisition-voucher-relation',
    templateUrl: '../requisition-voucher-relation-extended/requisition-voucher-relation-extended.component.html'
})
export class PurchaseOrderRequisitionVoucherRelation extends RequisitionVoucherRelationExtendedComponent implements OnInit, OnDestroy {
    constructor(
        protected requisitionVoucherRelationService: RequisitionVoucherRelationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(requisitionVoucherRelationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
