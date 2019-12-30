import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RequisitionVoucherRelationExtendedService } from './requisition-voucher-relation-extended.service';
import { RequisitionVoucherRelationComponent } from 'app/entities/requisition-voucher-relation';

@Component({
    selector: 'jhi-requisition-voucher-relation',
    templateUrl: './requisition-voucher-relation-extended.component.html'
})
export class RequisitionVoucherRelationExtendedComponent extends RequisitionVoucherRelationComponent implements OnInit, OnDestroy {
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
