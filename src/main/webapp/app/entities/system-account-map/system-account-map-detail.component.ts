import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';

@Component({
    selector: 'jhi-system-account-map-detail',
    templateUrl: './system-account-map-detail.component.html'
})
export class SystemAccountMapDetailComponent implements OnInit {
    systemAccountMap: ISystemAccountMap;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemAccountMap }) => {
            this.systemAccountMap = systemAccountMap;
        });
    }

    previousState() {
        window.history.back();
    }
}
