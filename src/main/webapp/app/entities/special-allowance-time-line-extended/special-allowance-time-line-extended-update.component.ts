import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineExtendedService } from './special-allowance-time-line-extended.service';
import { SpecialAllowanceTimeLineUpdateComponent } from 'app/entities/special-allowance-time-line';

@Component({
    selector: 'jhi-special-allowance-time-line-update',
    templateUrl: './special-allowance-time-line-extended-update.component.html'
})
export class SpecialAllowanceTimeLineExtendedUpdateComponent extends SpecialAllowanceTimeLineUpdateComponent implements OnInit {
    constructor(
        protected specialAllowanceTimeLineService: SpecialAllowanceTimeLineExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(specialAllowanceTimeLineService, activatedRoute);
    }
}
