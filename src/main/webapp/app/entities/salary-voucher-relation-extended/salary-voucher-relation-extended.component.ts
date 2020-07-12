import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ISalaryVoucherRelation, SalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SalaryVoucherRelationExtendedService } from './salary-voucher-relation-extended.service';
import { SalaryVoucherRelationComponent } from 'app/entities/salary-voucher-relation';

@Component({
    selector: 'jhi-salary-voucher-relation-extended',
    templateUrl: './salary-voucher-relation-extended.component.html'
})
export class SalaryVoucherRelationExtendedComponent extends SalaryVoucherRelationComponent implements OnInit, OnDestroy {
    salaryVoucherRelation: ISalaryVoucherRelation;
    eventSubscriber: Subscription;

    constructor(
        protected salaryVoucherRelationService: SalaryVoucherRelationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        private router: Router
    ) {
        super(salaryVoucherRelationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        this.salaryVoucherRelationService
            .query({
                'officeId.equals': this.salaryVoucherRelation.officeId,
                'year.equals': this.salaryVoucherRelation.year,
                'month.equals': this.salaryVoucherRelation.month,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ISalaryVoucherRelation[]>) => this.paginateSalaryVoucherRelations(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSalaryVoucherRelations();
        this.activatedRoute.data.subscribe(({ salaryVoucherRelation }) => {
            this.salaryVoucherRelation = salaryVoucherRelation;
            this.loadAll();
        });
    }

    gotoVoucher(salaryVoucherRelation: ISalaryVoucherRelation): void {
        if (salaryVoucherRelation.voucherNo.includes('JN'))
            this.router.navigate(['/journal-voucher/voucher-no', salaryVoucherRelation.voucherNo, 'edit']);
        else if (salaryVoucherRelation.voucherNo.includes('BP'))
            this.router.navigate(['/payment-voucher/voucher-no', salaryVoucherRelation.voucherNo, 'edit']);
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    previousState() {
        window.history.back();
    }
}
