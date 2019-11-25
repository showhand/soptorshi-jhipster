import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISalaryMessages } from 'app/shared/model/salary-messages.model';

@Component({
    selector: 'jhi-salary-messages-detail',
    templateUrl: './salary-messages-detail.component.html'
})
export class SalaryMessagesDetailComponent implements OnInit {
    salaryMessages: ISalaryMessages;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salaryMessages }) => {
            this.salaryMessages = salaryMessages;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
