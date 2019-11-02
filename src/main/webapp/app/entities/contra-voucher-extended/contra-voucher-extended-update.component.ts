import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';
import { ContraVoucherExtendedService } from 'app/entities/contra-voucher-extended/contra-voucher-extended.service';
import { ContraVoucherUpdateComponent } from 'app/entities/contra-voucher';

@Component({
    selector: 'jhi-contra-voucher-update',
    templateUrl: './contra-voucher-extended-update.component.html'
})
export class ContraVoucherExtendedUpdateComponent extends ContraVoucherUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected contraVoucherService: ContraVoucherExtendedService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, contraVoucherService, currencyService, activatedRoute);
    }
}
