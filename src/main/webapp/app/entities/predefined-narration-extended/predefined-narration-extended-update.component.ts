import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationExtendedService } from './predefined-narration-extended.service';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { PredefinedNarrationUpdateComponent } from 'app/entities/predefined-narration';

@Component({
    selector: 'jhi-predefined-narration-update',
    templateUrl: './predefined-narration-extended-update.component.html'
})
export class PredefinedNarrationExtendedUpdateComponent extends PredefinedNarrationUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected predefinedNarrationService: PredefinedNarrationExtendedService,
        protected voucherService: VoucherService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, predefinedNarrationService, voucherService, activatedRoute);
    }
}
