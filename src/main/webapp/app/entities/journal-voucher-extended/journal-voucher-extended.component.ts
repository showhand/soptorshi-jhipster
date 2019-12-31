import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { JournalVoucherExtendedService } from './journal-voucher-extended.service';
import { JournalVoucherComponent } from 'app/entities/journal-voucher';

@Component({
    selector: 'jhi-journal-voucher',
    templateUrl: './journal-voucher-extended.component.html'
})
export class JournalVoucherExtendedComponent extends JournalVoucherComponent implements OnInit, OnDestroy {
    constructor(
        protected journalVoucherService: JournalVoucherExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(journalVoucherService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
    back() {
        window.history.back();
    }
}
