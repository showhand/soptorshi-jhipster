import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMstGroup } from 'app/shared/model/mst-group.model';

@Component({
    selector: 'jhi-mst-group-detail',
    templateUrl: './mst-group-detail.component.html'
})
export class MstGroupDetailComponent implements OnInit {
    mstGroup: IMstGroup;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mstGroup }) => {
            this.mstGroup = mstGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
