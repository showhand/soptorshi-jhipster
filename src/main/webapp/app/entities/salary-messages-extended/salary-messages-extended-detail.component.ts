import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesDetailComponent } from 'app/entities/salary-messages';

@Component({
    selector: 'jhi-salary-messages-detail',
    templateUrl: './salary-messages-extended-detail.component.html'
})
export class SalaryMessagesExtendedDetailComponent extends SalaryMessagesDetailComponent implements OnInit {
    salaryMessages: ISalaryMessages;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
