import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManufacturer } from 'app/shared/model/manufacturer.model';

@Component({
    selector: 'jhi-manufacturer-detail',
    templateUrl: './manufacturer-detail.component.html'
})
export class ManufacturerDetailComponent implements OnInit {
    manufacturer: IManufacturer;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manufacturer }) => {
            this.manufacturer = manufacturer;
        });
    }

    previousState() {
        window.history.back();
    }
}
