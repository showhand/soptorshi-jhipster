/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialProductInfoDetailComponent } from 'app/entities/commercial-product-info/commercial-product-info-detail.component';
import { CommercialProductInfo } from 'app/shared/model/commercial-product-info.model';

describe('Component Tests', () => {
    describe('CommercialProductInfo Management Detail Component', () => {
        let comp: CommercialProductInfoDetailComponent;
        let fixture: ComponentFixture<CommercialProductInfoDetailComponent>;
        const route = ({ data: of({ commercialProductInfo: new CommercialProductInfo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialProductInfoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialProductInfoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialProductInfoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialProductInfo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
