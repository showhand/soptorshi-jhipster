import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdvance } from 'app/shared/model/advance.model';

@Component({
    selector: 'jhi-advance-detail',
    templateUrl: './advance-detail.component.html'
})
export class AdvanceDetailComponent implements OnInit {
    advance: IAdvance;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advance }) => {
            this.advance = advance;
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
