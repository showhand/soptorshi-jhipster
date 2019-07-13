/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { VendorContactPersonDetailComponent } from 'app/entities/vendor-contact-person/vendor-contact-person-detail.component';
import { VendorContactPerson } from 'app/shared/model/vendor-contact-person.model';

describe('Component Tests', () => {
    describe('VendorContactPerson Management Detail Component', () => {
        let comp: VendorContactPersonDetailComponent;
        let fixture: ComponentFixture<VendorContactPersonDetailComponent>;
        const route = ({ data: of({ vendorContactPerson: new VendorContactPerson(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [VendorContactPersonDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VendorContactPersonDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VendorContactPersonDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vendorContactPerson).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
