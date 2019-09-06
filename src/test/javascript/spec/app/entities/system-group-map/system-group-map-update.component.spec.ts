/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SystemGroupMapUpdateComponent } from 'app/entities/system-group-map/system-group-map-update.component';
import { SystemGroupMapService } from 'app/entities/system-group-map/system-group-map.service';
import { SystemGroupMap } from 'app/shared/model/system-group-map.model';

describe('Component Tests', () => {
    describe('SystemGroupMap Management Update Component', () => {
        let comp: SystemGroupMapUpdateComponent;
        let fixture: ComponentFixture<SystemGroupMapUpdateComponent>;
        let service: SystemGroupMapService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SystemGroupMapUpdateComponent]
            })
                .overrideTemplate(SystemGroupMapUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SystemGroupMapUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemGroupMapService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SystemGroupMap(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.systemGroupMap = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SystemGroupMap();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.systemGroupMap = entity;
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
