import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';

@Component({
    selector: 'jhi-terms-and-conditions-detail',
    templateUrl: './terms-and-conditions-detail.component.html'
})
export class TermsAndConditionsDetailComponent implements OnInit {
    termsAndConditions: ITermsAndConditions;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ termsAndConditions }) => {
            this.termsAndConditions = termsAndConditions;
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
