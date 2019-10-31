import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationExtendedService } from './inventory-sub-location-extended.service';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationUpdateComponent } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-inventory-sub-location-update-extended',
    templateUrl: './inventory-sub-location-update-extended.component.html'
})
export class InventorySubLocationUpdateExtendedComponent extends InventorySubLocationUpdateComponent implements OnInit {
    inventorySubLocation: IInventorySubLocation;
    isSaving: boolean;

    inventorylocations: IInventoryLocation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected inventorySubLocationService: InventorySubLocationExtendedService,
        protected inventoryLocationService: InventoryLocationService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, inventorySubLocationService, inventoryLocationService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ inventorySubLocation }) => {
            this.inventorySubLocation = inventorySubLocation;
        });
        this.inventoryLocationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInventoryLocation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInventoryLocation[]>) => response.body)
            )
            .subscribe(
                (res: IInventoryLocation[]) => (this.inventorylocations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.inventorySubLocation.id !== undefined) {
            this.subscribeToSaveResponse(this.inventorySubLocationService.update(this.inventorySubLocation));
        } else {
            this.subscribeToSaveResponse(this.inventorySubLocationService.create(this.inventorySubLocation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventorySubLocation>>) {
        result.subscribe(
            (res: HttpResponse<IInventorySubLocation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackInventoryLocationById(index: number, item: IInventoryLocation) {
        return item.id;
    }
}
