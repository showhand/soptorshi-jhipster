import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherExtendedService } from './journal-voucher-extended.service';
import { JournalVoucherUpdateComponent } from 'app/entities/journal-voucher';

@Component({
    selector: 'jhi-journal-voucher-update',
    templateUrl: './journal-voucher-extended-update.component.html'
})
export class JournalVoucherExtendedUpdateComponent extends JournalVoucherUpdateComponent implements OnInit {
    constructor(protected journalVoucherService: JournalVoucherExtendedService, protected activatedRoute: ActivatedRoute) {
        super(journalVoucherService, activatedRoute);
    }
}
