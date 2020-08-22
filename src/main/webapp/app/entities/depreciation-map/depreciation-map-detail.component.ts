import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepreciationMap } from 'app/shared/model/depreciation-map.model';

@Component({
    selector: 'jhi-depreciation-map-detail',
    templateUrl: './depreciation-map-detail.component.html'
})
export class DepreciationMapDetailComponent implements OnInit {
    depreciationMap: IDepreciationMap;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depreciationMap }) => {
            this.depreciationMap = depreciationMap;
        });
    }

    previousState() {
        window.history.back();
    }
}
