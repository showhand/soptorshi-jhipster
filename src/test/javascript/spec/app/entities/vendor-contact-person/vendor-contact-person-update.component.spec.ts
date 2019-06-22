/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { VendorContactPersonUpdateComponent } from 'app/entities/vendor-contact-person/vendor-contact-person-update.component';
import { VendorContactPersonService } from 'app/entities/vendor-contact-person/vendor-contact-person.service';
import { VendorContactPerson } from 'app/shared/model/vendor-contact-person.model';

describe('Component Tests', () => {
    describe('VendorContactPerson Management Update Component', () => {
        let comp: VendorContactPersonUpdateComponent;
        let fixture: ComponentFixture<VendorContactPersonUpdateComponent>;
        let service: VendorContactPersonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [VendorContactPersonUpdateComponent]
            })
                .overrideTemplate(VendorContactPersonUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VendorContactPersonUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendorContactPersonService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new VendorContactPerson(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.vendorContactPerson = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new VendorContactPerson();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.vendorContactPerson = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
