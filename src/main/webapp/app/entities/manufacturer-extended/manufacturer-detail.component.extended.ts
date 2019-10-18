import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerDetailComponent } from 'app/entities/manufacturer';

@Component({
    selector: 'jhi-manufacturer-detail-extended',
    templateUrl: './manufacturer-detail.component.extended.html'
})
export class ManufacturerDetailComponentExtended extends ManufacturerDetailComponent implements OnInit {
    manufacturer: IManufacturer;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manufacturer }) => {
            this.manufacturer = manufacturer;
        });
    }

    previousState() {
        window.history.back();
    }
}
