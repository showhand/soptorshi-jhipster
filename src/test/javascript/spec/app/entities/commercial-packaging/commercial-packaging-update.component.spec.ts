/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPackagingUpdateComponent } from 'app/entities/commercial-packaging/commercial-packaging-update.component';
import { CommercialPackagingService } from 'app/entities/commercial-packaging/commercial-packaging.service';
import { CommercialPackaging } from 'app/shared/model/commercial-packaging.model';

describe('Component Tests', () => {
    describe('CommercialPackaging Management Update Component', () => {
        let comp: CommercialPackagingUpdateComponent;
        let fixture: ComponentFixture<CommercialPackagingUpdateComponent>;
        let service: CommercialPackagingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPackagingUpdateComponent]
            })
                .overrideTemplate(CommercialPackagingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPackagingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPackagingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPackaging(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPackaging = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPackaging();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPackaging = entity;
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
