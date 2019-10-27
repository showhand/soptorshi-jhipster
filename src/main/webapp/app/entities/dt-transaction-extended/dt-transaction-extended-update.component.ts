import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionExtendedService } from './dt-transaction-extended.service';
import { ICreditorLedger } from 'app/shared/model/creditor-ledger.model';
import { CreditorLedgerService } from 'app/entities/creditor-ledger';
import { IDebtorLedger } from 'app/shared/model/debtor-ledger.model';
import { DebtorLedgerService } from 'app/entities/debtor-ledger';
import { IChequeRegister } from 'app/shared/model/cheque-register.model';
import { ChequeRegisterService } from 'app/entities/cheque-register';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';
import { DtTransactionService, DtTransactionUpdateComponent } from 'app/entities/dt-transaction';

@Component({
    selector: 'jhi-dt-transaction-update',
    templateUrl: './dt-transaction-extended-update.component.html'
})
export class DtTransactionExtendedUpdateComponent extends DtTransactionUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dtTransactionService: DtTransactionService,
        protected mstAccountService: MstAccountService,
        protected voucherService: VoucherService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, dtTransactionService, mstAccountService, voucherService, currencyService, activatedRoute);
    }
}
