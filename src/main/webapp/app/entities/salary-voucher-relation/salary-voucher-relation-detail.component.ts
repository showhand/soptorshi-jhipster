import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';

@Component({
    selector: 'jhi-salary-voucher-relation-detail',
    templateUrl: './salary-voucher-relation-detail.component.html'
})
export class SalaryVoucherRelationDetailComponent implements OnInit {
    salaryVoucherRelation: ISalaryVoucherRelation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salaryVoucherRelation }) => {
            this.salaryVoucherRelation = salaryVoucherRelation;
        });
    }

    previousState() {
        window.history.back();
    }
}
