import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockStatusDeleteDialogExtendedComponent,
    StockStatusDeletePopupExtendedComponent,
    StockStatusDetailExtendedComponent,
    StockStatusExtendedComponent,
    stockStatusExtendedRoute,
    stockStatusPopupExtendedRoute,
    StockStatusUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockStatusExtendedRoute, ...stockStatusPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockStatusExtendedComponent,
        StockStatusDetailExtendedComponent,
        StockStatusUpdateExtendedComponent,
        StockStatusDeleteDialogExtendedComponent,
        StockStatusDeletePopupExtendedComponent
    ],
    entryComponents: [
        StockStatusExtendedComponent,
        StockStatusUpdateExtendedComponent,
        StockStatusDeleteDialogExtendedComponent,
        StockStatusDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockStatusExtendedModule {}
