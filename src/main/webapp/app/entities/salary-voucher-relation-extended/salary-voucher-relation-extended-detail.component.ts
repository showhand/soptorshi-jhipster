import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { SalaryVoucherRelationDetailComponent } from 'app/entities/salary-voucher-relation';

@Component({
    selector: 'jhi-salary-voucher-relation-detail',
    templateUrl: './salary-voucher-relation-extended-detail.component.html'
})
export class SalaryVoucherRelationExtendedDetailComponent extends SalaryVoucherRelationDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
