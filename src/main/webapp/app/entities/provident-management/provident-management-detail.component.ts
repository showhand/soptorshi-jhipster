import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvidentManagement } from 'app/shared/model/provident-management.model';

@Component({
    selector: 'jhi-provident-management-detail',
    templateUrl: './provident-management-detail.component.html'
})
export class ProvidentManagementDetailComponent implements OnInit {
    providentManagement: IProvidentManagement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providentManagement }) => {
            this.providentManagement = providentManagement;
        });
    }

    previousState() {
        window.history.back();
    }
}
