import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { SalaryVoucherRelationExtendedService } from './salary-voucher-relation-extended.service';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';
import { SalaryVoucherRelationUpdateComponent } from 'app/entities/salary-voucher-relation';

@Component({
    selector: 'jhi-salary-voucher-relation-update',
    templateUrl: './salary-voucher-relation-extended-update.component.html'
})
export class SalaryVoucherRelationExtendedUpdateComponent extends SalaryVoucherRelationUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected salaryVoucherRelationService: SalaryVoucherRelationExtendedService,
        protected officeService: OfficeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, salaryVoucherRelationService, officeService, activatedRoute);
    }
}
