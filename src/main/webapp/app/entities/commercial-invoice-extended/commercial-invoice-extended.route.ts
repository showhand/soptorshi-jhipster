import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialInvoice, ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';
import { CommercialInvoiceExtendedService } from './commercial-invoice-extended.service';
import { CommercialInvoiceExtendedComponent } from './commercial-invoice-extended.component';
import { CommercialInvoiceDetailExtendedComponent } from './commercial-invoice-detail-extended.component';
import { CommercialInvoiceUpdateExtendedComponent } from './commercial-invoice-update-extended.component';
import { CommercialInvoiceDeletePopupExtendedComponent } from './commercial-invoice-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialInvoiceExtendedResolve implements Resolve<ICommercialInvoice> {
    constructor(private service: CommercialInvoiceExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialInvoice> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialInvoice>) => response.ok),
                map((commercialInvoice: HttpResponse<CommercialInvoice>) => commercialInvoice.body)
            );
        }
        return of(new CommercialInvoice());
    }
}

export const commercialInvoiceExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialInvoiceExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialInvoiceDetailExtendedComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialInvoiceUpdateExtendedComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialInvoiceUpdateExtendedComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialInvoicePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialInvoiceDeletePopupExtendedComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
