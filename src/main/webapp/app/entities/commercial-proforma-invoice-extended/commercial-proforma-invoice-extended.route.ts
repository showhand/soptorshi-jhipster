import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialProformaInvoice, ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceExtendedService } from './commercial-proforma-invoice-extended.service';
import { CommercialProformaInvoiceExtendedComponent } from './commercial-proforma-invoice-extended.component';
import { CommercialProformaInvoiceDetailExtendedComponent } from './commercial-proforma-invoice-detail-extended.component';
import { CommercialProformaInvoiceUpdateExtendedComponent } from './commercial-proforma-invoice-update-extended.component';
import { CommercialProformaInvoiceDeletePopupExtendedComponent } from './commercial-proforma-invoice-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialProformaInvoiceResolve implements Resolve<ICommercialProformaInvoice> {
    constructor(private service: CommercialProformaInvoiceExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialProformaInvoice> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialProformaInvoice>) => response.ok),
                map((commercialProformaInvoice: HttpResponse<CommercialProformaInvoice>) => commercialProformaInvoice.body)
            );
        }
        return of(new CommercialProformaInvoice());
    }
}

export const commercialProformaInvoiceExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialProformaInvoiceExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialProformaInvoiceDetailExtendedComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialProformaInvoiceUpdateExtendedComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialProformaInvoiceUpdateExtendedComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialProformaInvoicePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialProformaInvoiceDeletePopupExtendedComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
