import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockStatusComponent,
    StockStatusDetailComponent,
    StockStatusUpdateComponent,
    StockStatusDeletePopupComponent,
    StockStatusDeleteDialogComponent,
    stockStatusRoute,
    stockStatusPopupRoute
} from './';

const ENTITY_STATES = [...stockStatusRoute, ...stockStatusPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockStatusComponent,
        StockStatusDetailComponent,
        StockStatusUpdateComponent,
        StockStatusDeleteDialogComponent,
        StockStatusDeletePopupComponent
    ],
    entryComponents: [StockStatusComponent, StockStatusUpdateComponent, StockStatusDeleteDialogComponent, StockStatusDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockStatusModule {}
