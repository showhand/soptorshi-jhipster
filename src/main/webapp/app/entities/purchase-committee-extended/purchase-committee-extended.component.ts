import { PurchaseCommitteeComponent, PurchaseCommitteeService } from 'app/entities/purchase-committee';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Component } from '@angular/core';
import { IPurchaseCommittee } from 'app/shared/model/purchase-committee.model';
import { Subscription } from 'rxjs';

@Component({
    selector: 'jhi-purchase-committee',
    templateUrl: './purchase-committee-extended.component.html'
})
export class PurchaseCommitteeExtendedComponent extends PurchaseCommitteeComponent {
    displayedColumns: string[] = ['id', 'employeeFullName'];

    constructor(
        protected purchaseCommitteeService: PurchaseCommitteeService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(purchaseCommitteeService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
}
