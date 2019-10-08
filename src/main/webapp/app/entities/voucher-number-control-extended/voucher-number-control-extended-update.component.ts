import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { VoucherNumberControlExtendedService } from './voucher-number-control-extended.service';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { VoucherNumberControlUpdateComponent } from 'app/entities/voucher-number-control';

@Component({
    selector: 'jhi-voucher-number-control-update',
    templateUrl: './voucher-number-control-extended-update.component.html'
})
export class VoucherNumberControlExtendedUpdateComponent extends VoucherNumberControlUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected voucherNumberControlService: VoucherNumberControlExtendedService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected voucherService: VoucherService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, voucherNumberControlService, financialAccountYearService, voucherService, activatedRoute);
    }
}
